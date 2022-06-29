import React from 'react';
import { ToastContainer } from 'react-toastify';
import Navbar from '../../Components/Partials/Navbar';
import Profile from '../../Components/Users/Profile';

function ProfilePage() {
  return (
    <>
      <Navbar />
      <div>
        <ToastContainer />
        <Profile />
      </div>
    </>
  )
}

export default ProfilePage