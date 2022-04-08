import React from "react";
// import styles from "./App.module.css";
import Prueba from "./prueba";
import posts from "../data/posts";

const App = () => {
  return (
    <main className="h-full md:h-screen w-full">
      <section className="container mx-auto px-0 md:px-4 py-4">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 2xl:grid-cols-4 justify-items-center gap-4">
          {posts.map((element, index) => (
            <Prueba
              key={index}
              title={element.title}
              likes={element.likes}
              order={index + 1}
              image={element.image}
            />
          ))}
        </div>
      </section>
    </main>
  );
};

export default App;