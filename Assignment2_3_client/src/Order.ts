import { Food } from "./user/MenuItem"
import { Restaurant } from "./user/RestaurantItem"

export enum OrderStatus {
  "PENDING"="Pending",
  "ACCEPTED"="Accepted",
  "IN_DELIVERY"="In Delivery",
  "DELIVERED"="Delivered",
  "DECLINED"="Declined"
}

export type OrderItem = {
  id: number,
  item: Food,
  quantity: number
}

export type Order = {
  id: number,
  items: OrderItem[],
  restaurant: Restaurant
  status: OrderStatus
}

export const nextStatusAccept = (status: OrderStatus) => {
  switch(status){
     case OrderStatus.PENDING: return OrderStatus.ACCEPTED;
     case OrderStatus.ACCEPTED: return OrderStatus.IN_DELIVERY;
     case OrderStatus.IN_DELIVERY: return OrderStatus.DELIVERED;
     case OrderStatus.DELIVERED: return OrderStatus.DELIVERED;
     case OrderStatus.DECLINED: throw Error('Wrong status flow');
  }
}

export const toEnum = (status: string) => {
  status = status.toLowerCase();
  switch(status){
    case "pending": return OrderStatus.PENDING;
    case "accepted": return OrderStatus.ACCEPTED;
    case "in_delivery":
    case "in delivery": return OrderStatus.IN_DELIVERY;
    case "delivered": return OrderStatus.DELIVERED;
    case "declined": return OrderStatus.DECLINED
 }

 throw Error('Wrong status flow');
}


