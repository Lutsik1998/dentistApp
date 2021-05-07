import { Address } from "./user";

export interface OfficeInfoResponseModel {
    id: string,
    name: string,
    address: Address,
    phoneNumber: {number: string},
    listDoctorsId: Array<string>,
    nip: number
}
