import { Library } from "./library.model"

export interface Book {
    id: number,
    title: string,
    author: string,
    publisher: string,
    price: number,
    publishedYear: number,
    libraryID: number
}

export interface BookNoLibrary {
    id: number,
    title: string,
    author: string,
    publisher: string,
    price: number,
    publishedYear: number
}

export interface BookDetails {
    id: number,
    title: string,
    author: string,
    publisher: string,
    price: number,
    publishedYear: number,
    library: Library
}