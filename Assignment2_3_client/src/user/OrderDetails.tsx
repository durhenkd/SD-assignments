import { Order } from "../Order";

const OrderDetails = (props: Order) => {
  let price = 0;
  props.items.forEach((item) => {
    price += item.item.price * item.quantity;
  });

  return (
    <div className="flex flex-row shadow hover:shadow-xl hover:outline-1 w-full p-9 gap-2 bg-neutral-10 transition ease-in-out rounded-2xl justify-center items-center">
      <div className="flex flex-col grow">
        <h1 className="mb-3 text-2xl font-thin">{props.restaurant.name}</h1>
        {props.items.map((item) => <p>{`${item.item.name} | ${item.quantity} pcs | ${item.quantity*item.item.price} lei`}</p>)}
      </div>
      <div className="flex flex-col justify-center items-center ">
        <p>{`Total: ${price}`}</p>
        <p>{props.status}</p>
      </div>
    </div>
  );
};

export default OrderDetails;
