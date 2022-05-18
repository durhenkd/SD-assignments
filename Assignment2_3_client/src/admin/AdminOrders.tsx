import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { spring_axios } from "../axios";
import { nextStatusAccept, Order, OrderStatus, toEnum } from "../Order";
import { State } from "../state";
import * as BStyle from "../simple/ButtonStyle";

const AdminOrders = () => {
  const { id, isAdmin } = useSelector((state: State) => state.account);
  const navigate = useNavigate();
  const [orders, setOrders] = useState<Order[]>([]);

  const getNewOrderUpdatedStatus = (order_id: number, status: OrderStatus) : Order[] => {
    let temp = structuredClone(orders);
    let index = temp.findIndex((t) => t.id === order_id);
    temp[index].status = status;
    return temp;
  }

  const acceptOrder = (order_id: number, status: string) => {
    spring_axios
      .post(`/admin/${id}/orders/${order_id}/accept`)
      .then((res) => {setOrders(getNewOrderUpdatedStatus(order_id, nextStatusAccept(toEnum(status))))}); //todo get next
  };

  const rejectOrder = (order_id: number, status: string) => {
    spring_axios
      .post(`/admin/${id}/orders/${order_id}/accept`)
      .then((res) => {setOrders(getNewOrderUpdatedStatus(order_id, OrderStatus.DECLINED))});
  };

  useEffect(() => {
    if (id === -1 || !isAdmin) {
      navigate("/login");
    } else {
      spring_axios.get(`/admin/${id}/orders`).then((res) => {
        console.log(res.data);

        setOrders(res.data);
      });
    }
  }, [id, isAdmin, navigate]);

  return (
    <div className="w-2/3 flex flex-col gap-6">
      {orders.length !== 0
        ? orders.map((order) =>
            OrderAdminDetails(
              order,
              () => {
                acceptOrder(order.id, order.status);
              },
              () => {
                rejectOrder(order.id, order.status);
              }
            )
          )
        : "Loading..."}
    </div>
  );
};

const OrderAdminDetails = (
  props: Order,
  acceptCallback: Function,
  rejectCallback: Function
) => {
  let price = 0;
  props.items.forEach((item) => {
    price += item.item.price * item.quantity;
  });

  return (
    <div className="text-neutral-600 font-thin flex flex-row shadow hover:shadow-xl hover:outline-1 w-full p-9 gap-2 bg-neutral-10 transition ease-in-out rounded-2xl justify-center items-center">
      <div className="flex flex-col grow">
        {props.items.map((item) => (
          <p className="text-2xl">{`${item.item.name} | ${
            item.quantity
          } pcs | ${item.quantity * item.item.price} lei`}</p>
        ))}
      </div>
      <div className="flex flex-col justify-center items-center ">
        <p>{`Total: ${price}`}</p>
        <p>{props.status}</p>
        <button className={`${BStyle.getOutlineHoverFill("neutral")}`} onClick={()=>{acceptCallback()}}>
          Accept
        </button>
        <button className={`${BStyle.getOutlineHoverFill("neutral")}`} onClick={()=>{rejectCallback()}}>
          Reject
        </button>
      </div>
    </div>
  );
};

export default AdminOrders;
