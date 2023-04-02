import { BookNoLibrary } from "./book.model"
import { ReaderMembership } from "./reader.model"

export interface Library {
    id: number,
    name: string,
    address: string,
    email: string,
    website: string,
    yearOfConstruction: number
}

export interface LibraryDetails {
    id: number,
    name: string,
    address: string,
    email: string,
    website: string,
    yearOfConstruction: number,
    books: BookNoLibrary[],
    readers: ReaderMembership[]
}

export interface LibraryMembership {
    id: number,
    name: string,
    address: string,
    email: string,
    website: string,
    yearOfConstruction: number,
    startDate: Date,
    endDate: Date
}

export interface LibraryCount {
    id: number,
    name: string,
    totalCount: number
}

export interface AddUpdateLibraryDTO {
    name: string,
    address: string,
    email: string,
    website: string,
    yearOfConstruction: number
}