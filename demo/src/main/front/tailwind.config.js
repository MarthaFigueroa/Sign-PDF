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

        
        'xs': {'min': '454px'},
        'mobile': {'max': '639px'},
        // sm	640px	@media (min-width: 640px) { ... }
        // md	768px	@media (min-width: 768px) { ... }
        'smd': {'min': '874px'},
        // lg	1024px	@media (min-width: 1024px) { ... }
        // xl	1280px	@media (min-width: 1280px) { ... }
        // 2xl	1536px	@media (min-width: 1536px) { ... }
        // 870-1023
      }
    },
  },
  plugins: [],
}