import { Component, OnInit, inject } from '@angular/core';
import {Observable} from 'rxjs';
import {Router} from '@angular/router';
import { CartStore } from './cart.store';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  // NOTE: you are free to modify this component

  private router = inject(Router)
  private cartStore = inject(CartStore)

  itemCount: Observable<number> = this.cartStore.itemCount$;

  ngOnInit(): void {
  }

  checkout(): void {
    this.router.navigate([ '/checkout' ])
  }

  isCartInvalid(): boolean {
    var cartSize = 0;
    this.cartStore.itemCount$.subscribe((data) => {
      cartSize = data;
    })

    if(cartSize > 0) {
      return false;
    }

    return true;

  }

}

// this.cartStore.itemCount$
// .subscribe((data) => {
//   console.log(">>>Num line items:", data)
// })
