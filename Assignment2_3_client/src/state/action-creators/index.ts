import { Dispatch } from "redux";
import { AccountActionType, CartActionType } from "../action-types/ActionType";
import { AccountAction } from "../actions/accountActions";
import { CartAction } from "../actions/cartActions";

// ! =========== ACCOUNT ACTIONS ===========
export const userLogin = (name: string, id: number) => {
  return (dispatch: Dispatch<AccountAction>) => {
    dispatch({
      type: AccountActionType.USER_LOGIN,
      payload: {
        name: name,
        id: id
      }
    })
  }
}

export const adminLogin = (name: string, id: number) => {
  return (dispatch: Dispatch<AccountAction>) => {
    dispatch({
      type: AccountActionType.ADMIN_LOGIN,
      payload: {
        name: name,
        id: id
      }
    })
  }
}

export const logOut = () => {
  return (dispatch: Dispatch<AccountAction>) => {
    dispatch({
      type: AccountActionType.LOGOUT,
    })
  }
}

// ! =========== CART ACTIONS ===========

export const add_to_order = (id: number, name: string, price: number, restaurant_id: number) => {
  return (dispatch: Dispatch<CartAction>) => {
    dispatch({
      type: CartActionType.ADD_FOOD,
      payload: {
        id: id,
        name: name,
        price: price,
        restaurant_id: restaurant_id
      }
    })
  }
}

export const remove_from_order = (id: number) => {
  return (dispatch: Dispatch<CartAction>) => {
    dispatch({
      type: CartActionType.REMOVE_FOOD,
      payload: id
    })
  }
}

export const remove_all_from_order = () => {
  return (dispatch: Dispatch<CartAction>) => {
    dispatch({
      type: CartActionType.REMOVE_ALL_FOOD
    })
  }
}