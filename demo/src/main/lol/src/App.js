// import logo from './logo.svg';
import './App.css';
import Navbar from './Components/Navbar';
import Drop from './Components/Drop';
import './public/css/navbar.css'
import './public/css/tailwind.css'
// import List from './Components/List';
import CardDiv from './Components/CardDiv';

function App() {
  return (
    <>
      <Navbar />
      <div className="bg-red-500">
        <Drop />
        <h1 className='text-white'>kk</h1>
        <button className='bg-gray-600 p-1 text-white rounded hover:bg-green-500'>kkkkkkkkkkk</button>
      </div>

      <CardDiv />

      {/* <List /> */}
    </>
  );
}

export default App;
