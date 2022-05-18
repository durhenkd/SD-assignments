


export type Food = {
  id: number;
  name: string;
  description: string;
  imageUrl: string;
  foodCategory: string;
  price: number;
}

const MenuItem = (props: Food) => {
  return (
    <div className="shadow cursor-pointer hover:shadow-xl hover:outline-1 w-full p-9 flex flex-col gap-2 bg-neutral-10 transition ease-in-out rounded-2xl">
      <div className="w-full flex flex-row gap-6 items-end">
        <h2 className="font-thin text-neutral-800 text-5xl">{props.name}</h2>
        <h6 className="text-neutral-600">{props.foodCategory}</h6>
      </div>
        <p className="text-xl">{`${props.price} lei | ${props.description}`}</p>
    </div>
  );
};


export default MenuItem;