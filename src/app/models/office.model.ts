export interface OfficeInfoResponseModel {
    id: string,
    name: string,
    address: Address,
    phoneNumber: {number: string},
    listDoctorsId: Array<string>,
    nip: number
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