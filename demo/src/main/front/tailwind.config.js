module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}", './public/index.html'],
  theme: {
    extend: {
      colors: {
        'regal-blue': '#243c5a',
        'active-item': '#0077b6',
        'icon-group': '#0392dd ',
        'toggle-span': '#0077b6'
      },
      screens: {
        'mobile': {'max': '639px'},
        'smd': '874px'
      }
    },
  },
  plugins: [],
}