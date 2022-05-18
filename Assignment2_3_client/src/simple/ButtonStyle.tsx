
import { Color } from "./Colors"

// ! Component style describe margin, color, padding, etc.
// ! NOT: size

export type ButtonStyle =
  | "primaryFillHoverOutline"
  | "neutralFillHoverOutline"
  | "primaryOutlineHoverFill"
  | "neutralOutlineHoverFill"
  | "primaryEmptyHoverOutline"
  | "neutralEmptyHoverOutline"
  | "primaryEmptyHoverEmpty"
  | "neutralEmptyHoverEmpty";

const baseStyle = " my-2 px-5 py-3 border-2 rounded-2xl transition ease-in-out";

export const getFillHoverOutline = (color: Color) => {
  return baseStyle + ` 
  hover:bg-transparent
  ${color === "primary" ? "bg-primary-800" : "bg-neutral-800"}
  ${color === "primary" ? "border-primary-800" : "border-neutral-800"}
  ${color === "primary" ? "text-primary-10" : "text-neutral-10"}
  ${color === "primary" ? "hover:text-primary-800" : "hover:text-neutral-800"}
  ${color === "primary" ? "" : ""}
  ${color === "primary" ? "" : ""}
  ${color === "primary" ? "" : ""}
  ${color === "primary" ? "" : ""}
  `;
};

export const getOutlineHoverFill = (color: Color) => {
  return baseStyle + ` 
  bg-transparent
  ${color === "primary" ? "border-primary-800" : "border-neutral-800"}
  ${color === "primary" ? "text-primary-800" : "text-neutral-800"}
  ${color === "primary" ? "hover:text-primary-10" : "hover:text-neutral-10"}
  ${color === "primary" ? "hover:bg-primary-800" : "hover:bg-neutral-800"}
  ${color === "primary" ? "" : ""}
  ${color === "primary" ? "" : ""}
  ${color === "primary" ? "" : ""}
  `;
};

export const getEmptyHoverOutline = (color: Color) => {
  return baseStyle + ` 
  bg-transparent
  border-transparent
  ${color === "primary" ? "hover:border-primary-800" : "hover:border-neutral-800"}
  ${color === "primary" ? "text-primary-400" : "text-neutral-400"}
  ${color === "primary" ? "hover:text-primary-800" : "hover:text-neutral-800"}
  ${color === "primary" ? "hover:text-primary-800" : "hover:text-neutral-800"}
  ${color === "primary" ? "active:bg-primary-800" : "active:bg-neutral-800"}
  ${color === "primary" ? "active:text-primary-10" : "active:text-neutral-10"}
  ${color === "primary" ? "" : ""}
  `;
};

export const getEmptyHoverEmpty = (color: Color) => {
  return baseStyle + ` 
  bg-transparent
  border-transparent
  ${color === "primary" ? "text-primary-800" : "text-neutral-800"}
  ${color === "primary" ? "hover:text-primary-800" : "hover:text-neutral-800"}
  ${color === "primary" ? "" : ""}
  ${color === "primary" ? "" : ""}
  ${color === "primary" ? "" : ""}
  ${color === "primary" ? "" : ""}
  `;
};