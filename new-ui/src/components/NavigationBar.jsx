import {useDispatch, useSelector} from "react-redux";
import {Link} from "react-router-dom";
import styles from "../Css/NavigationBar.module.css";
import {useContext} from "react";
import {LoginContext} from "../context/LoginContext.jsx";
import TextField from '@mui/material/TextField';
import {setSearch} from "../redux/actions.js";

const NavigationBar = () => {

    const role = useSelector((state) => state.role);
    const search = useSelector((state) => state.search);
    const dispatch = useDispatch();
    const {handleLogout} = useContext(LoginContext);

    return (
        <>
            <nav className={styles["navbar-wrapper"]}>

                <div className={styles["logo-wrapper"]}>
                    <Link style={{marginLeft:"20px"}} to="/">TalentSphere</Link>
                </div>

                {role && <div>
                    <ul className={styles["nav-links"]}>
                        <li>
                            <Link to="/home">Home</Link>
                        </li>
                        {role === 'ADMIN' && (
                            <>
                                <li>
                                    <Link to="/home/addCompany">Add Company</Link>
                                </li>
                                <li>
                                    <Link to="/home/addManager">Add Manager</Link>
                                </li>
                                <li>
                                    <Link to="/home/approveComment">Approve Comment</Link>
                                </li>
                            </>

                        )}
                        {role === 'MANAGER' && (
                            <>
                                <li>
                                    <Link to="/home/personals">Personals</Link>
                                </li>
                                <li>
                                    <Link to="/home/calendar">Calendar</Link>
                                </li>
                                <li>
                                    <Link to="/home/approveHoliday">Approve Holiday</Link>
                                </li>
                                <li>
                                    <Link to="/home/payment">Payment</Link>
                                </li>
                                <li>
                                    <Link to="/home/approvePayment">Approve Payment</Link>
                                </li>
                            </>
                        )}
                        {role === 'PERSONAL' && (
                            <>
                                <li>
                                    <Link to="/home/calendar">Calendar</Link>
                                </li>
                                <li>
                                    <Link to="/home/comment">Comment</Link>
                                </li>
                                <li>
                                    <Link to="/home/payment">Payment</Link>
                                </li>
                            </>
                        )}
                        {role === 'VISITOR' && (
                            <>
                                {/* visitor-service getInformation (pageable) */}
                                {/* visitor-service getInformationByCompanyName */}
                                <li>
                                    <TextField
                                        id="search"
                                        label="Search"
                                        variant="outlined"
                                        className={styles["search-link"]}
                                        value={search} onChange={e => dispatch(setSearch(e.target.value))} />
                                </li>
                            </>
                        )}
                    </ul>
                </div>}

                <ul className={styles["nav-links"]}>
                    <li>
                        {!role && <Link to="/register">Register</Link>}
                    </li>
                    <li>
                        {role ? <button onClick={() => handleLogout()}>Logout</button> : <Link to="/login">Login</Link>}
                    </li>
                    <li>
                        {!role && <Link to="/admin">Admin</Link>}
                    </li>
                </ul>
            </nav>
        </>
    )
}

export default NavigationBar;