/** @type {import('tailwindcss').Config} */
export default {
  content: [
    // 🔑 FIXED: Added ** to make sure Tailwind reads files inside all subfolders (like templates/)
    "./src/main/resources/**/*.html",
    "./src/main/resources/**/*.js"
  ],
  theme: {
    extend: {},
  },
  plugins: [],
  darkMode: "class",
};