import { useEffect, useState } from "react";
import { Food } from "../user/MenuItem";
import { spring_axios } from "../axios";
import { useSelector } from "react-redux";
import { State } from "../state/reducers";
import * as BStyle from "../simple/ButtonStyle";
import { useNavigate } from "react-router-dom";
import AdminMenuItem from "./AdminMenuItem";
import * as IStyle from "../simple/InputStyle";

const AdminHome = () => {
  const [menuItems, setMenuItems] = useState<Food[]>([]);
  const [shownItems, setShownItems] = useState<Food[]>([]);
 // const [shownCategory, setShownCategory] = useState<string>("");

  const { id, isAdmin } = useSelector((state: State) => state.account);
  const navigate = useNavigate();

  //! here we show the menu, along with navigation to order
  useEffect(() => {
    if (id === -1 || !isAdmin) navigate("/login");
    else
      spring_axios.get(`/admin/${id}/menu`).then((res) => {
        setMenuItems(res.data);
        setShownItems(res.data);
      });
  }, [id, isAdmin, navigate]);

  return (
    <div className=" flex flex-col w-full h-full  items-center">
      <div className=" w-1/2 flex flex-row gap-5 justify-center mb-10 ">
        <button
          onClick={() => {
            navigate("/admin/add");
          }}
          className={` ${BStyle.getFillHoverOutline("neutral")}`}
        >
          Add Food
        </button>
        <button
          onClick={() => {
            navigate("/admin/orders");
          }}
          className={` ${BStyle.getFillHoverOutline("neutral")}`}
        >
          See Orders
        </button>
        <select
          className={` flex-grow ${IStyle.getToggleStyle("neutral")}`}
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
      <div className="w-1/2 flex flex-col gap-5 justify-center items-center">
        
        {shownItems.length !== 0
          ? shownItems.map((food) => (
              <AdminMenuItem
                key={food.id}
                description={food.description}
                imageUrl={food.imageUrl}
                foodCategory={food.foodCategory}
                id={food.id}
                name={food.name}
                price={food.price}
              />
            ))
          : "No items to show :("}
      </div>
    </div>
  );
};

export default AdminHome;
