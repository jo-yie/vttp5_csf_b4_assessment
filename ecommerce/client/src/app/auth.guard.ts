import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { CartStore } from './cart.store';

export const authGuard: CanActivateFn = (route, state) => {

    const cartStore = inject(CartStore)
    const router = inject(Router)

    var itemCount!: number

    cartStore.itemCount$.subscribe((data) => {
        itemCount = data
    })

    if (itemCount > 0) {
      return true
    }
  
    router.navigate(['/'])
    return false
  
};