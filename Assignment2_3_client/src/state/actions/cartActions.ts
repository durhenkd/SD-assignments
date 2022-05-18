
import { CartActionType as typ } from "../action-types/ActionType"

interface AddFoodAction {
  type: typ.ADD_FOOD,
  payload: {
    id: number,
    name: string
    price: number
    restaurant_id: number
  }
}

interface RemoveFoodAction {
  type: typ.REMOVE_FOOD,
  payload: number
}

interface RemoveAllFoodAction{
  type: typ.REMOVE_ALL_FOOD
}

export type CartAction = AddFoodAction | RemoveFoodAction | RemoveAllFoodAction