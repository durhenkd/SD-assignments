import * as IStyle from "../simple/InputStyle";
import * as BStyle from "../simple/ButtonStyle";

import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { spring_axios } from "../axios";
import { useSelector } from "react-redux";
import { State } from "../state";

type MenuItemFormState = {
  name: string;
  description: string;
  imageUrl: string;
  foodCategory: string;
  price: string;
};

const isNumber = (s: string): boolean => {
  try {
    if (s === "") return true;

    var toReturn = Number.parseFloat(s);
    console.log(toReturn);
    return !Number.isNaN(toReturn);
  } catch (error) {
    return false;
  }
};

const AdminAddFood = () => {
  const navigate = useNavigate();

  const [error, setError] = React.useState<string>("");
  const [formState, setFormState] = React.useState<MenuItemFormState>({
    name: "",
    description: "",
    imageUrl: "",
    foodCategory: "",
    price: "",
  });

  const { id, isAdmin } = useSelector((store: State) => store.account);

  useEffect(() => {
    if( id === -1 || !isAdmin){
      navigate("/admin")
    } 
  }, [id, isAdmin, navigate])

  const formHandler = (event: React.FormEvent) => {
    event.preventDefault();
    setError("");

    //* checking for validity
    if (
      formState.name === "" ||
      formState.description === "" ||
      formState.imageUrl === "" ||
      formState.foodCategory === "" ||
      formState.price === ""
    ) {
      setError("Please fill out every field of the form");
      return;
    }

    try {
      var price = Number.parseFloat(formState.price);
    } catch (error) {
      setError("Not a proper number");
      return;
    }

    let dto = {
      name: formState.name,
      description: formState.description,
      imageUrl: formState.imageUrl,
      foodCategory: formState.foodCategory,
      price: price
    }

    spring_axios
      .post(`/admin/${id}/menu`, dto, {withCredentials: true})
      .then((res) => {
        navigate("/admin");
      })
      .catch((err) => {
        setError(err.message);
        console.log(err);
      });
  };

  return (
    <div className="w-1/3 h-full flex flex-col flex-wrap justify-center items-center">
      <form onSubmit={formHandler} className="flex flex-col w-full items-center ">
        <h1 className="text-7xl  mb-11 font-bold">Add Item</h1>

        <input
          className={`grow w-full ${IStyle.getSimpleStyle("neutral")}`}
          type="text"
          placeholder="Name"
          value={formState.name}
          onChange={(v) => {
            setFormState({
              ...formState,
              name: v.target.value,
            }); //! making a copy then overwriting
          }}
        />
        <input
          className={`grow w-full ${IStyle.getSimpleStyle("neutral")}`}
          type="text"
          placeholder="Description"
          value={formState.description}
          onChange={(v) => {
            setFormState({
              ...formState,
              description: v.target.value,
            }); //! making a copy then overwriting
          }}
        />
        <input
          className={`grow w-full ${IStyle.getSimpleStyle("neutral")}`}
          type="text"
          placeholder="ImageUrl"
          value={formState.imageUrl}
          onChange={(v) => {
            setFormState({
              ...formState,
              imageUrl: v.target.value,
            }); //! making a copy then overwriting
          }}
        />

        <select
          className={`grow w-full ${IStyle.getSimpleStyle("primary")}`}
          onChange={(v) =>
            setFormState({
              ...formState,
              foodCategory: v.target.value,
            })
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
          <option value="ASIA">Asian</option>
        </select>

        <input
          className={`grow w-full ${IStyle.getSimpleStyle("neutral")}`}
          type="text"
          placeholder="Price"
          value={formState.price}
          onChange={(v) => {
            const newValue = v.target.value;
            if (!isNumber(newValue)) return;

            setFormState({
              ...formState,
              price: newValue,
            }); //! making a copy then overwriting
          }}
        />

        {error && <h4 className="text-red-700 text-lg">{error}</h4>}
        <button
          onClick={() => {}}
          className={`grow w-full ${BStyle.getFillHoverOutline("primary")}`}
        >
          Add Menu Item!
        </button>
      </form>
    </div>
  );
};

export default AdminAddFood;
