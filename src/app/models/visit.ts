
export interface Visit {
    dateTimeStart: string,
    dateTimeEnd:  string,
    information: string,
    doctorId: string,
    officeId: string,
    patientId: string 
}

export interface VisitResponseModel {
    id: string,
    dateTimeStart: string,
    dateTimeEnd:  string,
    information: string,
    doctorId: string,
    officeId: string,
    patientId: string 
    review: Review
}

export interface VisitListItemModel {
    id: string,
    dateTimeStart: Date,
    dateTimeEnd:  Date,
    information: string,
    doctorId: string,
    officeId: string,
    patientId: string 
    review: Review
}

export interface Review {
    text: string,
    rating: number
}
