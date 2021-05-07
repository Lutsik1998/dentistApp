import { Gender, UserRole } from "../enums/various.enum"
import { Address } from "./user";

export interface Doctor {
    id: string;
}

export interface DoctorInfoResponseModel {
    id:	string,
    email: string,
    password: string,
    roles: UserRole,
    firstName: string,
    secondName: string,
    lastName: string,
    pesel: number,
    birthDate: Date,
    sex: Gender,
    phoneNumber: {number: string},
    address: Address,
    licence: string,
    specialization: string[]
}