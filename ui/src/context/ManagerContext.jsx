import { createContext, useContext, useState, useEffect } from "react";
import { ApiContext } from "./ApiContext";
import PropTypes from "prop-types";
import {useDispatch, useSelector} from "react-redux";
import {setData, addPersonal, addCompany, addHoliday, deletePersonal, deleteCompany, deleteHoliday} from "../redux/actions.js";

export const ManagerContext = createContext();

export const ManagerContextProvider = ({ children }) => {
    const userProfile = useSelector((state) => state.userProfile);
    const data = useSelector((state) => state.data);
    const token = useSelector((state) => state.token);
    const authId = useSelector((state) => state.authId);
    const dispatch = useDispatch();
    const { apiGet, apiPost, apiPatch, apiDelete } = useContext(ApiContext);
    const [isLoading, setIsLoading] = useState(false);
    const [manager, setManager] = useState({
        companies: [],
        personals: [],
        holidays: []
    });

    async function getData() {
        setIsLoading(true);
        const responseDataCompanies = await apiGet(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_COMPANY_URL}${process.env.REACT_APP_FIND_ALL_BY_MANAGER_ID_URL}?managerId=${userProfile.id}`, token);
        setManager(prevState => ({...prevState, companies: responseDataCompanies.data}));

        const responseDataPersonals = await apiGet(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_PERSONAL_URL}${process.env.REACT_APP_FIND_ALL_BY_MANAGER_ID_URL}?managerId=${userProfile.id}`, token);
        setManager(prevState => ({...prevState, personals: responseDataPersonals.data}));

        setManager(prevState => ({...prevState, holidays: prevState.companies.map(company =>
                apiGet(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_HOLIDAY_URL}${process.env.REACT_APP_FIND_ALL_BY_MANAGER_ID_URL}?companyId=${company.id}`, token).then(responseData => responseData.data)
            )}));
        // todo: data başarılı mı değil mi kontrol et ona göre setleme yap
        dispatch(setData(manager))
        setIsLoading(false);
    }

    async function handleAddPersonal(personal){
        setIsLoading(true);
        const responseData = await apiPost(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_PERSONAL_URL}${process.env.REACT_APP_SAVE_URL}`, personal, token);
        dispatch(addPersonal(responseData.data));
        setIsLoading(false);
    }

    async function handleDeletePersonal(id){
        setIsLoading(true);
        const responseData = await apiDelete(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_PERSONAL_URL}${process.env.REACT_APP_DELETE_BY_ID_URL}?id=${id}\``, token);
        dispatch(deletePersonal(id));
        setIsLoading(false);
    }

    async function handleAddCompany(company){
        setIsLoading(true);
        const responseData = await apiPost(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_COMPANY_URL}${process.env.REACT_APP_SAVE_URL}`, company, token);
        dispatch(addCompany(responseData.data));
        setIsLoading(false);
    }

    async function handleDeleteCompany(id){
        setIsLoading(true);
        const responseData = await apiDelete(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_COMPANY_URL}${process.env.REACT_APP_DELETE_BY_ID_URL}?id=${id}\``, token);
        dispatch(deleteCompany(id));
        setIsLoading(false);
    }

    async function handleAddHoliday(holiday){
        setIsLoading(true);
        const responseData = await apiPost(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_HOLIDAY_URL}${process.env.REACT_APP_SAVE_URL}`, holiday, token);
        dispatch(addHoliday(responseData.data));
        setIsLoading(false);
    }

    async function handleDeleteHoliday(id){
        setIsLoading(true);
        const responseData = await apiDelete(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_HOLIDAY_URL}${process.env.REACT_APP_DELETE_BY_ID_URL}?id=${id}\``, token);
        dispatch(deleteHoliday(id));
        setIsLoading(false);
    }

    return (
        <ManagerContext.Provider
            value={{
                getData,
                handleAddPersonal,
                handleDeletePersonal,
                handleAddCompany,
                handleDeleteCompany,
                handleAddHoliday,
                handleDeleteHoliday,
                isLoading,
            }}
        >
            {children}
        </ManagerContext.Provider>
    );
};

ManagerContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
};