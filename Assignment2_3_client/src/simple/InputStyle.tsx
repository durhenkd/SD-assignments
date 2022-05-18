
import { Color } from "./Colors"

// ! Component style describe margin, color, padding, etc.
// ! NOT: size

export type InputStyle = "primaryToggleStyle" | "neutralToggleStyle" | "primaryOutlineStyle" | "neutralOutlineStyle";

export function getToggleStyle (color: Color) :string 
{
  return ` my-2 px-5 py-3 border-2 rounded-2xl bg-transparent 
  ${color === "primary" ? "border-primary-200"                  : "border-neutral-200"}
  ${color === "primary" ? "placeholder:text-primary-200"        : "placeholder:text-neutral-200"} 
  ${color === "primary" ? "hover:border-primary-800"            : "hover:border-neutral-800"} 
  ${color === "primary" ? "hover:placeholder:text-primary-800"  : "hover:placeholder:text-neutral-800"} 
  ${color === "primary" ? "focus:bg-primary-800"                : "focus:bg-neutral-800"} 
  ${color === "primary" ? "focus:text-primary-10"               : "focus:text-neutral-10"} 
  ${color === "primary" ? "focus:border-primary-800"            : "focus:border-neutral-800"} 
  ${color === "primary" ? "focus:placeholder:text-primary-10"   : "focus:placeholder:text-neutral-10"}  
  focus:outline-none
  transition ease-in-out`;
}


export function getSimpleStyle  (color: Color): string 
{ 
  return ` my-2 px-5 py-3 border-2 rounded-2xl bg-transparent 
  ${color === "primary" ? "border-primary-200"                  : "border-neutral-200"}
  ${color === "primary" ? "placeholder:text-primary-200"        : "placeholder:text-neutral-200"} 
  ${color === "primary" ? "hover:border-primary-800"            : "hover:border-neutral-800"} 
  ${color === "primary" ? "hover:placeholder:text-primary-800"  : "hover:placeholder:text-neutral-800"} 
  ${color === "primary" ? "focus:border-primary-800"            : "focus:border-neutral-800"} 
  ${color === "primary" ? "focus:placeholder:text-primary-800"   : "focus:placeholder:text-neutral-800"}  
  focus:outline-none
  transition ease-in-out`;
}

