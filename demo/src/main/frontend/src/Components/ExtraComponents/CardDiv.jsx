import React from "react";
import Card from "./Card";
import posts from "../data/posts";

const App = ({key, doc, url}) => {

    // useEffect(() => {
    //     URL.createObjectURL(doc.OriginalFile.File);
    // });
    
  return (
    <main className="bg-gray-100 h-full md:h-screen w-full">
      <section className="container mx-auto px-0 md:px-4 py-4">
        <div className="grid grid-cols-4 md:grid-cols- lg:grid-cols-3 2xl:grid-cols-4 justify-items-center gap-4">
          {/* {posts.map((element, index) => ( */}
            <Card
              key={key}
            //   title={element.title}
            //   likes={element.likes}
            //   order={index + 1}
            //   image={element.image}
              doc={doc}
              url={url}
            />
          {/* ))} */}
        </div>
      </section>
    </main>
  );
};

export default App;