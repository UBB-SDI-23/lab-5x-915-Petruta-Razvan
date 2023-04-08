import random
import psycopg2
from faker import Faker


def insert_data_books():
    conn = psycopg2.connect(
        host='localhost',
        port='5432',
        database='librarydb',
        user='postgres',
        password='password'
    )

    try:
        with open("./queries/insert_books.sql", "w", encoding="utf-8") as f:
            fake = Faker()
            with conn.cursor() as cursor:
                cursor.execute("SELECT ID from libraries")
                library_ids = [el[0] for el in cursor.fetchall()]
                insert_query = "INSERT INTO books (title, author, publisher, price, published_year, library_id) VALUES "
                values = []
                for i in range(1000000):
                    title = fake.sentence(nb_words=random.randint(2, 5), variable_nb_words=True,
                                          ext_word_list=None).strip(".")
                    author = fake.name()
                    publisher = fake.company()
                    price = round(random.uniform(10, 100), 2)
                    published_year = random.randint(1850, 2022)
                    library_id = random.choice(library_ids)
                    values.append(f"('{title}', '{author}', '{publisher}', {price}, {published_year}, {library_id})")
                    if len(values) == 1000:
                        f.write(insert_query + ", ".join(values) + ";\n")
                        values = []
    except Exception as error:
        print(error)
    finally:
        if conn:
            cursor.close()
            conn.close()
