import {useSelector} from "react-redux";
import {Link} from "react-router-dom";
import styles from "../Css/NavigationBar.module.css";
import {useContext} from "react";
import {LoginContext} from "../context/LoginContext.jsx";

const NavigationBar = () => {

    const role = useSelector((state) => state.role);
    const {handleLogout} = useContext(LoginContext);

    return (
        <>
            <nav className={styles["navbar-wrapper"]}>

                <div className={styles["logo-wrapper"]}>
                    <Link to="/">Talent Sphere</Link>
                </div>

                {role && <div>
                    <ul className={styles["nav-links"]}>
                        <li>
                            <Link to="/home">Home</Link>
                        </li>
                        {role === 'ADMIN' && (
                            <>
                                {/* admin-service findByAuthID */}
                                {/* admin-service getInformation */}
                                {/* admin-service update */}
                                {/* admin-service delete */}
                                <li>
                                    <Link to="/addCompany">Add Company</Link>
                                    {/* company-service save*/}
                                </li>
                                <li>
                                    <Link to="/addPersonal">Add Personal</Link>
                                    {/*  company-service findAllWithoutManager  */}
                                    {/*  manager-service save  */}
                                </li>
                                <li>
                                    <Link to="/approveComment">Approve Comment</Link>
                                    {/*  comment-service findAllByNotApprove  */}
                                    {/*  comment-service approveCommentById  */}
                                </li>
                            </>

                        )}
                        {role === 'MANAGER' && (
                            <>
                                {/* manager-service findByAuthID */}
                                {/* manager-service getInformation */}
                                {/* manager-service update */}
                                {/* manager-service delete */}
                                <li>
                                    <Link to="/"></Link>
                                </li>
                            </>
                        )}
                        {role === 'PERSONAL' && (
                            <>
                                {/* personal-service findByAuthID */}
                                {/* personal-service getInformation */}
                                {/* personal-service update */}
                                {/* personal-service delete */}
                                <li>
                                    <Link to="/calendar">Calendar</Link>
                                    {/*  holiday-service saveByPersonal  */}
                                </li>
                                <li>
                                    <Link to="/comment">Comment</Link>
                                    {/*  comment-service saveByPersonal  */}
                                </li>
                            </>
                        )}
                        {role === 'VISITOR' && (
                            <>
                                {/* visitor-service findByAuthID */}
                                {/* visitor-service getInformation (pageable) */}
                                {/* visitor-service getInformationByCompanyName */}
                                {/* visitor-service update */}
                                {/* visitor-service delete */}
                                <li>
                                    {/*  search  */}
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
                        {role ? <button onClick={() => handleLogout}>Logout</button> : <Link to="/login">Login</Link>}
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