import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { spring_axios } from "../axios";
import { Order } from "../Order";
import { State } from "../state";
import OrderDetails from "./OrderDetails";

const OrderList = () => {
  const { id, isAdmin } = useSelector((state: State) => state.account);
  const navigate = useNavigate();
  const [orders, setOrders] = useState<Order[]>([]);

  useEffect(() => {
    if (id === -1 || isAdmin) {
      navigate("/login");
    }
    else {
      spring_axios.get(`/user/order/${id}`).then((res) => {
        setOrders(res.data);
      });
    }
  }, [id, isAdmin, navigate]);

  return <div className="w-2/3 flex flex-col gap-6">{(orders.length !== 0) ? orders.map((order) => OrderDetails(order)) : "Loading..."}</div>;
};

export default OrderList;
