import React from 'react';
// import { ToastContainer } from 'react-toastify';
// import NavBar from '../../Components/NavBar.jsx'
// import 'react-toastify/dist/ReactToastify.css';
import Drop from '../../Components/Drop';
import CardDiv from '../../Components/CardDiv';
import Navbar from '../../Components/Navbar';

const ListDocs = () => {
    
    return(
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
    )
}

export default ListDocs;