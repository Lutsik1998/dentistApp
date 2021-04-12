import { UserRole } from "../enums/various.enum";

export interface User {
    email: string;
    password: string;
    role: UserRole;
}