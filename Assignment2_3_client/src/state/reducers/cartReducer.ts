import { CartActionType } from "../action-types/ActionType"
import { CartAction } from "../actions/cartActions"

export type CartItem = {
  name: string,
  price: number,
  quantity: number,
  id: number
}

export type OrderState= {
  restaurant_id: number,
  cart: CartItem[]
}

const defaultState: OrderState = {restaurant_id: -1, cart:[]}

const cartReducer = (state: OrderState = defaultState, action: CartAction) => {
  switch(action.type){
    case CartActionType.ADD_FOOD: 
      return addFood(state, action.payload)
    case CartActionType.REMOVE_ALL_FOOD:
      return defaultState;
    case CartActionType.REMOVE_FOOD:
      return removeFood(state, action.payload)
    default: return state;
  }
}

export default cartReducer;

function addFood(state: OrderState = defaultState, payload: {id: number, name: string, price: number, restaurant_id:number}){
  
  let temp = structuredClone(state);
  let item = temp.cart.filter((order_item) => order_item.id === payload.id);
  temp.restaurant_id = payload.restaurant_id;
  if (item.length !== 0){
    item[0].quantity += 1;
  }else
    temp.cart.push({ id: payload.id, name:payload.name, quantity:1, price:payload.price})

  return temp; 
}

function removeFood(state: OrderState = defaultState, id: number){
  let temp = structuredClone(state);
  let item = temp.cart.filter((order_item) => order_item.id === id);
  if (item.length !== 0)
    item[0].quantity -= 1;
  
  temp.cart = temp.cart.filter((r) => r.quantity > 0);
  return temp; 
}