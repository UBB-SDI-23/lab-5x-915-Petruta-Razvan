import { LibraryMembership } from "./library.model"

export interface Reader {
    id: number,
    name: string,
    email: string,
    birthDate: Date,
    gender: string,
    student: boolean
}

export interface ReaderDetails {
    id: number,
    name: string,
    email: string,
    birthDate: Date,
    gender: string,
    student: boolean,
    libraries: LibraryMembership[]
}

export interface ReaderMembership {
    id: number,
    name: string,
    email: string,
    birthDate: Date,
    gender: string,
    student: boolean,
    startDate: Date,
    endDate: Date
}

export interface AddUpdateReaderDTO {
    name: string,
    email: string,
    birthDate: Date,
    gender: string,
    student: boolean
}