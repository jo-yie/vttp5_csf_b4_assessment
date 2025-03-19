
// TODO Task 2
// Use the following class to implement your store

import { Injectable, OnDestroy } from "@angular/core"
import { ComponentStore } from "@ngrx/component-store"
import { Cart, LineItem } from "./models";

@Injectable()
export class CartStore extends ComponentStore<Cart> {

    constructor() {
        // initialise state with empty array
        super({ lineItems: [] });

    }

    readonly addLineItem = this.updater((state, lineItem: LineItem) => ({
        ...state,
        lineItems: [...state.lineItems, lineItem]
    }));

    // num of items in cart
    readonly itemCount$ = this.select((state) => state.lineItems.length);

    // selectors: functions to read state
    readonly lineItems$ = this.select((state) => state.lineItems);

}
