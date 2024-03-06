import styles from "../Css/HomePage.module.css";
import {UserProfileContextProvider} from "../context/UserProfileContext.jsx";
import {UserProfile} from "../components/UserProfile.jsx";
import AddCompany from "../components/AddCompany.jsx";
import AddManager from "../components/AddManager.jsx";
import {useSelector} from "react-redux";
import {ApproveComment} from "../components/ApproveComment.jsx";
import {AdminHome} from "../components/AdminHome.jsx";
import {ManagerHome} from "../components/ManagerHome.jsx";
import MyCalendar from "../components/MyCalendar.jsx";
import {ApproveHoliday} from "../components/ApproveHoliday.jsx";
import {PersonalHome} from "../components/PersonalHome.jsx";
import AddComment from "../components/AddComment.jsx";
import {VisitorHome} from "../components/VisitorHome.jsx";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import Personal from "../components/Personal.jsx";
import AddPayment from "../components/AddPayment.jsx";
import {ApprovePayment} from "../components/ApprovePayment.jsx";

const HomePage = ({page}) => {

    const role = useSelector((state) => state.role);
    const userProfile = useSelector((state) => state.userProfile);
    const navigate = useNavigate();

    useEffect(() => {
        if((page === 'addCompany' && role !== 'ADMIN') ||
            (page === 'addManager' && role !== 'ADMIN') ||
            (page === 'approveComment' && role !== 'ADMIN') ||
            (page === 'personals' && role !== 'MANAGER') ||
            (page === 'calendar' && (role !== 'MANAGER' || role !== 'PERSONAL')) ||
            (page === 'approveHoliday' && role !== 'MANAGER') ||
            (page === 'payment' && role !== 'MANAGER') ||
            (page === 'comment' && role !== 'PERSONAL')
        ) {
            navigate('/home');
        }else if(role === 'MANAGER'){

        }else if(role === 'PERSONAL'){

        }else if(role === 'VISITOR'){

        }else if(role === ''){
            navigate('/')
        }
    }, []);

    return (
        <>
            {role &&
            <div className={styles["home-page-wrapper"]}>
                <div className={styles["user-profile-wrapper"]}>
                    <UserProfileContextProvider>
                        <UserProfile/>
                    </UserProfileContextProvider>
                </div>
                {userProfile.id &&
                <div className={styles["content-wrapper"]}>
                    {role === 'ADMIN' && <>
                        {page === 'home' && <AdminHome/>}
                        {page === 'addCompany' && <AddCompany/>}
                        {page === 'addManager' && <AddManager/>}
                        {page === 'approveComment' && <ApproveComment/>}
                    </>}
                    {role === 'MANAGER' && <>
                        {page === 'home' && <ManagerHome/>}
                        {page === 'personals' && <Personal/>}
                        {page === 'calendar' && <MyCalendar/>}
                        {page === 'approveHoliday' && <ApproveHoliday/>}
                        {page === 'payment' && <AddPayment/>}
                        {page === 'approvePayment' && <ApprovePayment/>}
                    </>}
                    {role === 'PERSONAL' && <>
                        {page === 'home' && <PersonalHome/>}
                        {page === 'calendar' && <MyCalendar/>}
                        {page === 'comment' && <AddComment/>}
                        {page === 'payment' && <AddPayment/>}
                    </>}
                    {role === 'VISITOR' && <>
                        {page === 'home' && <VisitorHome/>}
                    </>}

                </div>
                }
            </div>
            }
        </>
    )
}

export default HomePage;