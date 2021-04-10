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