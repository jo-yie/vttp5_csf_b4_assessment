import { Component, EventEmitter, Input, OnInit, Output, inject } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {LineItem} from '../models';
import { CartStore } from '../cart.store';
import { from, pipe, take } from 'rxjs';

@Component({
  selector: 'app-order-form',
  templateUrl: './order-form.component.html',
  styleUrl: './order-form.component.css'
})
export class OrderFormComponent implements OnInit {

  // NOTE: you are free to modify this component

  constructor(private cartStore: CartStore) {}
  // private cartStore = inject(CartStore);

  private fb = inject(FormBuilder)

  @Input({ required: true })
  productId!: string

  @Input({ required: true })
  productName!: string

  @Input({ required: true })
  productPrice!: number

  form!: FormGroup

  ngOnInit(): void {
    this.form = this.createForm()

  }

  addToCart() {
    const lineItem: LineItem = {
      prodId: this.productId,
      quantity: this.form.value['quantity'],
      name: this.productName,
      price: this.productPrice
    }

    console.log(">>>Line item:", lineItem);

    this.cartStore.addLineItem(lineItem);

    this.cartStore.lineItems$
      .pipe(take(1))
      .subscribe((lineItems) => {
        console.log('>>>Line Items in Store:', lineItems);
    });

    this.cartStore.itemCount$
      .subscribe((data) => {
        console.log(">>>Num line items:", data)
      })

    this.form = this.createForm()
  }

  private createForm(): FormGroup {
    return this.fb.group({
      quantity: this.fb.control<number>(1, [ Validators.required, Validators.min(1) ])
    })
  }

}
