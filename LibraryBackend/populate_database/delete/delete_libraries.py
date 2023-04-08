import psycopg2


def delete_data_libraries():
    conn = psycopg2.connect(
        host='localhost',
        port='5432',
        database='librarydb',
        user='postgres',
        password='password'
    )

    try:
        with conn.cursor() as cursor:
            cursor.execute("DELETE FROM libraries;")
            conn.commit()
    except Exception as error:
        print(error)
    finally:
        if conn:
            cursor.close()
            conn.close()
