import { AccountActionType } from "../action-types/ActionType"
import { AccountAction } from "../actions/accountActions"

export type AccountState = {
  name: string,
  isAdmin: boolean,
  id: number
}

const defaultState: AccountState = {name: "", isAdmin: false, id: -1}

const accountReducer = (state: AccountState = defaultState, action: AccountAction) => {
  switch(action.type){
    case AccountActionType.USER_LOGIN:
      return {...state, name: action.payload.name, id:action.payload.id, isAdmin: false }
    case AccountActionType.ADMIN_LOGIN:
      return {...state, name: action.payload.name, id:action.payload.id, isAdmin: true }
    case AccountActionType.LOGOUT:
      return defaultState;
    default: return state;
  }
}

export default accountReducer;