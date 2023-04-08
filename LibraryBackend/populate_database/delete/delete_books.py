import psycopg2
from constants import HOST, PORT, DATABASE, USER, PASSWORD


def delete_data_books():
    conn = psycopg2.connect(
        host=HOST,
        port=PORT,
        database=DATABASE,
        user=USER,
        password=PASSWORD
    )

    try:
        with conn.cursor() as cursor:
            cursor.execute("DELETE FROM books;")
            conn.commit()
    except Exception as error:
        print(error)
    finally:
        if conn:
            cursor.close()
            conn.close()
