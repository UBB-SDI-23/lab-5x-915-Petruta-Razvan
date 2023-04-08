import random
import psycopg2
from faker import Faker


def insert_data_readers():
    conn = psycopg2.connect(
        host='localhost',
        port='5432',
        database='librarydb',
        user='postgres',
        password='password'
    )

    try:
        with open("./queries/insert_readers.sql", "w", encoding="utf-8") as f:
            fake = Faker()
            with conn.cursor() as cursor:
                insert_query = "INSERT INTO readers (name, email, birth_date, gender, is_student) VALUES "
                values = []
                for i in range(1000000):
                    name = fake.name()
                    name_modified = "".join(c for c in name if c not in [".", " ", ","]).lower()
                    email = name_modified + random.choice(["@gmail.com", "@yahoo.com"])
                    year = random.randint(1940, 2010)
                    month = random.randint(1, 12)
                    day = random.randint(1, 28)
                    date = f"{year}-{'{:02d}'.format(month)}-{'{:02d}'.format(day)}"
                    gender = random.choice(["male", "female"])
                    is_student = random.choice([True, False])
                    values.append(f"('{name}', '{email}', '{date}', '{gender}', {is_student})")
                    if len(values) == 1000:
                        f.write(insert_query + ", ".join(values) + ";\n")
                        values = []
    except Exception as error:
        print(error)
    finally:
        if conn:
            cursor.close()
            conn.close()
