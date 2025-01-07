/** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/main/resources/**/*.{html,js}"],
  theme: {
    extend: {
      screens:{
        'sm': '640px',
        // ... add other screens
    }
    },
  },
  plugins: [],

  darkMode: "selector", // or 'media' or 'class'
}

