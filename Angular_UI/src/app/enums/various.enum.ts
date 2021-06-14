export enum UserRole {
    doctor = "[ROLE_DOCTOR]",
    patient = "[ROLE_PATIENT]",
    admin = "[ROLE_DOCTOR, ROLE_ADMIN]"
}

export enum Gender {
    man = "MAN",
    woman = "WOMEN",
}

export enum ToothStatus {
    healthy = "HEALTHY",
    inProgress = "IN_PROGRESS",
    removed = "REMOVED",
    notHealthy = "NOT_HEALTHY"
}