
const colors = require('tailwindcss/colors')

module.exports = {
  content: [
    "./public/index.html",
    "./src/**/*.{js,jsx,ts,tsx}"
  ],
  theme: {
    screens: {
      sm: '480px',
      md: '768px',
      lg: '976px',
      xl: '1440px',
    },
    colors: {
      transparent: 'transparent',
      red: colors.red,
      black: colors.black,
      "primary": { 
        10:  "#FFFBEB",
        50:  "#FFEFC2",
        100: "#FFE499", 
        200: "#FFD561", 
        300: "#FFC529",
        400: "#FFB132",
        500: "#FF9C3B",
        600: "#FF8744",
        700: "#FE724C",
        800: "#E76845",
        900: "#D25F3F",
        1000: "B2221F"
      },
      "neutral": {
        10:  "#FAFAFA",
        50:  "#EBEBEB",
        100: "#D7D7D7",
        200: "#C1C2C2",
        300: "#ABADAD",
        400: "#959898",
        500: "#7F8283",
        600: "#696D6E",
        700: "#535859",
        800: "#3D4344",
        900: "#272D2F",
        1000: "#111111"
      }
    }
    // fontFamily: {
    //   sans: ['Graphik', 'sans-serif'],
    //   serif: ['Merriweather', 'serif'],
    // },
    // extend: {
    //   spacing: {
    //     '128': '32rem',
    //     '144': '36rem',
    //   },
      // borderRadius: {
      //   '4xl': '2rem',
      // }
    // }
  },
  plugins: [],
}
