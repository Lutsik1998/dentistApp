import { Recipe } from "./recipe";

export interface Visit {
    dateTimeStart: string,
    dateTimeEnd:  string,
    information: string,
    doctorId: string,
    officeId: string,
    patientId: string,
    review: Review
}

export interface VisitResponseModel {
    id: string,
    dateTimeStart: string,
    dateTimeEnd:  string,
    information: string,
    doctorId: string,
    officeId: string,
    patientId: string, 
    review: Review,
    recipes: Recipe[];
}

export interface VisitListItemModel {
    id: string,
    dateTimeStart: Date,
    dateTimeEnd:  Date,
    information: string,
    doctorId: string,
    officeId: string,
    patientId: string 
    review: Review,
    recipes: Recipe[]
}

export interface Review {
    text: string,
    rating: number
}
