import { Gender, UserRole } from "../enums/various.enum";
import { Address } from "./user";

export interface Patient {
    id: string;
    email: string;
    password: string;
    role: string;
    firstName: string;
    secondName: string;
    lastName: string;
    pesel: number;
    birthDate : Date;
    sex: string;
    addres: object;
    phoneNumber: object;
    cardNumber: number;
}

export interface PatientInfoResponseModel {
    id:	string,
    email: string,
    password: string,
    roles: UserRole[],
    firstName: string,
    secondName: string,
    lastName: string,
    pesel: number,
    birthDate: Date,
    sex: Gender,
    phoneNumber: {number: string},
    address: Address,
    cardNumber: number,
}

export interface PatientUpdateRequestModel {
    email: string,
    password: string,
    roles: string[],
    firstName: string,
    secondName: string,
    lastName: string,
    pesel: number,
    birthDate: Date,
    sex: Gender,
    phoneNumber: {number: string},
    address: Address,
    cardNumber: number,
}