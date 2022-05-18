import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { spring_axios } from "../axios";
import { State } from "../state";
import MenuItem, { Food } from "./MenuItem";
import { Restaurant } from "./RestaurantItem";
import * as IStyle from "../simple/InputStyle";

import { actionCreators } from "../state/index";
import { bindActionCreators } from "redux";
import { useDispatch } from "react-redux";

const Menu = () => {
  const { restaurant_id } = useParams<"restaurant_id">();

  const [menuItems, setMenuItems] = useState<Food[]>([]);
  const [restaurant, setRestaurant] = useState<Restaurant>();
  const [shownItems, setShownItems] = useState<Food[]>([]);

  const { id, isAdmin } = useSelector((state: State) => state.account);
  const navigate = useNavigate();

  const dispatch = useDispatch();
  const { add_to_order } = bindActionCreators(actionCreators, dispatch);

  useEffect(() => {
    if (id === -1 || isAdmin) {
      navigate("/login");
    } else {
      spring_axios.get(`/user/restaurant/${restaurant_id}`).then((res: any) => {
        setRestaurant(res.data);
      });

      spring_axios
        .get(`/user/restaurant/${restaurant_id}/menu`)
        .then((res: any) => {
          setShownItems(res.data);
          setMenuItems(res.data);
        });
    }
  }, [id, isAdmin, navigate, restaurant_id]);

  return (
    <div className="w-2/3 flex flex-col">
      <div className="my-6 flex flex-col items-center gap-4">
        <h1 className="text-8xl font-thin text-neutral-800">
          {restaurant ? restaurant.name : "Loading..."}
        </h1>
        <h3 className="text-xl text-neutral-600">
          {restaurant ? restaurant.location : "I wonder where..."}
        </h3>
        <select
          className={`w-full font-semibold ${IStyle.getToggleStyle("neutral")}`}
          onChange={(v) =>
            setShownItems(
              menuItems.filter((food) => food.foodCategory === v.target.value)
            )
          }
        >
          <option value="DESERT">Desert</option>
          <option value="PASTA">Pasta</option>
          <option value="BURGER">Burger</option>
          <option value="RED_MEAT">Red Meat</option>
          <option value="CHICKEN">Chicken</option>
          <option value="SEA_FRUITS">Sea Fruits</option>
          <option value="SALAD">Salad</option>
          <option value="BREAKFAST">Breakfast</option>
          <option value="COFFEE">Coffee</option>
          <option value="MILKSHAKE">Milkshake</option>
          <option value="SMOOTHIE">Smoothie</option>
          <option value="ALCOHOL">Alcohol</option>
          <option value="SOUP">Soup</option>
          <option value="ASIAN">Asian</option>
        </select>
      </div>
      <div className="w-full flex flex-col gap-4">
        {shownItems.length !== 0
          ? shownItems.map((r) => (
              <div
                onClick={(v) => {
                  add_to_order(
                    r.id,
                    r.name,
                    r.price,
                    restaurant_id ? Number.parseInt(restaurant_id) : -1
                  );
                }}
              >
                {MenuItem(r)}
              </div>
            ))
          : "Loading..."}
      </div>
    </div>
  );
};

export default Menu;
