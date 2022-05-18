import { CartItem } from "../state/reducers/cartReducer";
import { actionCreators, State } from "../state/index";
import { bindActionCreators } from "redux";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import { spring_axios } from "../axios";
import * as BStyle from "../simple/ButtonStyle";

const Cart = () => {
  const { restaurant_id, cart } = useSelector((state: State) => state.cart);
  const { id, isAdmin } = useSelector((state: State) => state.account);
  const navigate = useNavigate();

  const dispatch = useDispatch();
  const { add_to_order, remove_all_from_order, remove_from_order } =
    bindActionCreators(actionCreators, dispatch);

  useEffect(() => {
    if (id === -1 || isAdmin) {
      navigate("/login");
    } else {
    }
  }, [id, isAdmin, navigate]);

  const Item = (props: CartItem) => {
    return (
      <div className="shadow cursor-pointer hover:shadow-xl hover:outline-1 w-full p-9 flex flex-col gap-2 bg-neutral-10 transition ease-in-out rounded-2xl">
        <div className="w-full flex flex-row gap-6 items-center">
          <h2 className="grow font-thin text-neutral-800 text-5xl">
            {props.name}
          </h2>
          <div className="self-end flex flex-col">
            <div className="flex flex-row gap-2 items-center">
              <button
                className={`grow ${BStyle.getFillHoverOutline("neutral")}`}
                onClick={() => {
                  remove_from_order(props.id);
                }}
              >
                -
              </button>
              <button
                className={`grow ${BStyle.getFillHoverOutline("neutral")}`}
                onClick={() =>
                  add_to_order(
                    props.id,
                    props.name,
                    props.price,
                    props.quantity
                  )
                }
              >
                +
              </button>
            </div>
            <h2>{`${props.price} lei x ${props.quantity} = ${
              props.price * props.quantity
            }`}</h2>
          </div>
        </div>
      </div>
    );
  };

  const getPrice = () => {
    let items_nr = cart.reduce(
      (prev, item) => {
        prev.price += item.price * item.quantity;
        prev.quantity += item.quantity;
        return prev;
      },
      { quantity: 0, price: 0 }
    );
    return items_nr.price;
  };

  const order = () => {
    if (cart.length === 0) return;

    let ids: number[] = [];
    let quantity: number[] = [];

    cart.forEach((element) => {
      ids.push(element.id);
      quantity.push(element.quantity);
    });

    let orderDTO = { userId: id, itemIdList: ids, quantityList: quantity };

    spring_axios
      .post(`/user/restaurant/${restaurant_id}`, orderDTO)
      .then((res) => {
        remove_all_from_order();
        navigate("/restaurant");
      });
  };

  return (
    <div className="w-2/3 flex flex-col gap-6 items-center">
      <h1 className="my-10 text-7xl font-thin">{`Your total is: ${getPrice()} lei`}</h1>
      {cart.map((item) => Item(item))}
      <div className="h-40"></div>
      <div className="flex bg-neutral-10 w-full absolute bottom-0 justify-center">
        <div className="w-2/3 p-8 flex flex-row gap-5 justify-center">
          <button
            className={`${BStyle.getEmptyHoverOutline("neutral")}`}
            onClick={() => {
              remove_all_from_order();
            }}
          >
            Remove All From Cart
          </button>
          <button
            className={`grow ${BStyle.getFillHoverOutline("primary")}`}
            onClick={() => {
              order();
            }}
          >
            Order
          </button>
        </div>
      </div>
    </div>
  );
};

export default Cart;
