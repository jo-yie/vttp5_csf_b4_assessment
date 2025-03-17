import { Component, inject, Inject, OnInit } from '@angular/core';
import { Cart, LineItem, Order } from '../models';
import { CartStore } from '../cart.store';
import { take } from 'rxjs';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-confirm-checkout',
  templateUrl: './confirm-checkout.component.html',
  styleUrl: './confirm-checkout.component.css'
})
export class ConfirmCheckoutComponent implements OnInit {
  // task 3

  private cartStore = inject(CartStore);
  private fb = inject(FormBuilder);
  private productService = inject(ProductService);
  
  // observable for line items
  cart$ = this.cartStore.lineItems$;
  // local variable to store cart
  cartItems: LineItem[] = [];
  cartObject: Cart = { lineItems: [] };
  total: number = 0;

  form!: FormGroup;

  ngOnInit(): void {
    this.cart$.subscribe(items => {
      this.cartItems = items;
      console.log(">>>items", items);
    })

    this.cartItems.forEach((item) => {
      this.cartObject?.lineItems.push(item)
    })

    console.log(">>>Cart Object:", this.cartObject?.lineItems)

    this.calculateTotal();
    this.form = this.createForm();
  }

  calculateTotal() { 
    this.cartItems.forEach((item) => {
      var priceTemp = item.price * item.quantity
      this.total = this.total + priceTemp;
    })
  }

  createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control<string>("", [ Validators.required ]),
      address: this.fb.control<string>("", [ Validators.required, Validators.minLength(3) ]),
      priority: this.fb.control<boolean>(false),
      comments: this.fb.control<string>(""),
      cart: this.cartObject
    });
    
  }

  processForm() {
    // const values = this.form.value; 
    const values: Order = this.form.value
    console.log(">>>Form values:", values);
    this.productService.checkout(values);
  }

}
