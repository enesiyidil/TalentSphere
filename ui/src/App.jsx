import React from 'react';
import { Routes, Route, BrowserRouter as Router, } from 'react-router-dom';
import '../src/Css/navbar/navbar.css';
import RegisterPage from './components/register';
import NavigationBar from './components/NavigationBar';
import UserLoginForm from './components/login';
import AdminLoginForm from './components/adminlogin';
import ManagerandVisitor from './components/managerandvisitor';


function App() {
  return (
    <Router>
        
            <NavigationBar/>
              <Routes>
              <Route path="/"></Route>
              <Route path="/admin" element={<AdminLoginForm />} />
              <Route path="/login" element={<UserLoginForm/>}></Route>
              <Route path="/register" element={<RegisterPage />}></Route>
              <Route path="/managerpage" element={<ManagerandVisitor/>}></Route>
              </Routes>
          
              
   
    </Router>
    
  );
}

export default App;
