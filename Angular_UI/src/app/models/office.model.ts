import { Address } from "./user";

export interface OfficeInfoResponseModel {
    id: string,
    name: string,
    address: Address,
    phoneNumber: {number: string},
    listDoctorsId: Array<string>,
    nip: number
}

export const newOfficeTemplate = {
    name: "nazwa",
    address: {
      country: "kraj",
      region: "region",
      city: "miasto",
      postalCode: "00-000",
      street: "ulica",
      houseNr: "dom",
      roomNr: "nr",
      information: "informacje"
    },
    phoneNumber: {
      number: "000000000"
    },
    nip: 0
  }
