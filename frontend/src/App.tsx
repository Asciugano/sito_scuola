import { useState } from 'react'
import './App.css'
import { Navigate, Route, Routes} from 'react-router-dom'
import LoginPage from './pages/loginPage'
import SingUpPage from './pages/SingUpPage'
import HomePage from './pages/HomePage'
import CoursePage from './pages/CoursePage'
import AssignmentPage from './pages/AssignmentPage'
import ProfilePage from './pages/ProfilePage'

const App = () => {
    const [authUser, setAuthUser] = useState({ id: 1 })

    return (
        <>
            <h1 className='font-bold text-2xl text-red-500'>HOME</h1>
            <Routes>
                <Route path='/' element={authUser !== null ? <HomePage /> : <Navigate to="/login" />} />
                <Route path='/login' element={<LoginPage />} />
                <Route path='/singup' element={<SingUpPage />} />
                <Route path='/courses' element={authUser !== null ? <CoursePage /> : <Navigate to="/login" />} />
                <Route path='/assignment' element={authUser !== null ? <AssignmentPage /> : <Navigate to="/login" />} />
                <Route path='/profile' element={authUser !== null ? <ProfilePage /> : <Navigate to="/login" />} />
            </Routes>
        </>
    )
}

export default App
