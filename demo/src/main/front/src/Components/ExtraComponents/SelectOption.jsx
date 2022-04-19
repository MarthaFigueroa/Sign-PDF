// import React, { useState } from "react";
// import SelectSearch, { fuzzySearch } from "react-select-search";
// import "./../../public/css/SelectStyles.css";

// function SelectOption() {

//   const [value, setValue] = useState("");

//   const options = [
//     {
//       name: "Annie Cruz",
//       value: "annie.cruz",
//       photo: "https://randomuser.me/api/portraits/women/60.jpg"
//     },
//     {
//       name: "Eli Shelton",
//       disabled: true,
//       value: "eli.shelton",
//       photo: "https://randomuser.me/api/portraits/men/7.jpg"
//     },
//     {
//       name: "Loretta Rogers",
//       value: "loretta.rogers",
//       photo: "https://randomuser.me/api/portraits/women/51.jpg"
//     },
//     {
//       name: "Lloyd Fisher",
//       value: "lloyd.fisher",
//       photo: "https://randomuser.me/api/portraits/men/34.jpg"
//     },
//     {
//       name: "Tiffany Gonzales",
//       value: "tiffany.gonzales",
//       photo: "https://randomuser.me/api/portraits/women/71.jpg"
//     }
//   ];

//   return (
//     <>
//       <SelectSearch
//           autoComplete="off"
//           key={1}
//           options={options}
//           value={value}
//           onChange={setValue}
//           search
//           filterOptions={fuzzySearch}
//           placeholder="Search something"
//         />
//     </>
//   )
// }

// export default SelectOption