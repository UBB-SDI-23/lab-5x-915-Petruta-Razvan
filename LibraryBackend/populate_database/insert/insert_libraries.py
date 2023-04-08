import random
import psycopg2
from faker import Faker


def insert_data_libraries():
    conn = psycopg2.connect(
        host='localhost',
        port='5432',
        database='librarydb',
        user='postgres',
        password='password'
    )

    try:
        with open("./queries/insert_libraries.sql", "w", encoding="utf-8") as f:
            fake = Faker()
            with conn.cursor() as cursor:
                insert_query = "INSERT INTO libraries (name, address, email, website, year_of_construction) VALUES "
                values = []
                for i in range(1000000):
                    name = fake.company()
                    name_modified = "".join(c for c in name if c not in [".", " ", ","]).lower()
                    address = fake.address()
                    email = name_modified + random.choice(["@gmail.com", "@yahoo.com"])
                    website = name_modified + random.choice([".com", ".net", ".ro"])
                    year_of_construction = random.randint(1850, 2022)
                    values.append(f"('{name}', '{address}', '{email}', '{website}', {year_of_construction})")
                    if len(values) == 1000:
                        f.write(insert_query + ", ".join(values) + ";\n")
                        values = []
    except Exception as error:
        print(error)
    finally:
        if conn:
            cursor.close()
            conn.close()
