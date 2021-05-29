import { UserRole } from "../enums/various.enum";

export interface User {
    email: string;
    password: string;
    role: UserRole;
}

export interface Address {
    country: string,
    region: string,
    city: string,
    postalCode: string,
    street: string,
    houseNr: string,
    roomNr: string,
    information: string,
}