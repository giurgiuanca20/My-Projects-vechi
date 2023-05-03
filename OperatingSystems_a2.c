#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <dirent.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdbool.h>
void list(char path[500], int size, char permission[50]) ///////////////2
{
    DIR *dir = opendir(path);
    struct dirent *entry;
    struct stat buf;
    if (dir == 0)
        printf("ERROR\n invalid directory path\n");
    else
    {
        while ((entry = readdir(dir)) != 0)
        {

            if (strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0)
            {
                char finalPath[500];
                snprintf(finalPath, 500, "%s/%s", path, entry->d_name);
                lstat(finalPath, &buf);
                int validS = 1, validP = 1;
                if (size != -1)
                {
                    if (S_ISREG(buf.st_mode))
                    {
                        if (size < buf.st_size)
                            validS = 0;
                        if (S_ISDIR(buf.st_mode))
                            validS = 0;
                    }
                }

                if (strcmp(permission, ""))
                {
                    if (strcmp(permission, ""))
                    {
                        char permissions[40];

                        permissions[0] = (buf.st_mode & S_IRUSR) ? 'r' : '-';
                        permissions[1] = (buf.st_mode & S_IWUSR) ? 'w' : '-';
                        permissions[2] = (buf.st_mode & S_IXUSR) ? 'x' : '-';
                        permissions[3] = (buf.st_mode & S_IRGRP) ? 'r' : '-';
                        permissions[4] = (buf.st_mode & S_IWGRP) ? 'w' : '-';
                        permissions[5] = (buf.st_mode & S_IXGRP) ? 'x' : '-';
                        permissions[6] = (buf.st_mode & S_IROTH) ? 'r' : '-';
                        permissions[7] = (buf.st_mode & S_IWOTH) ? 'w' : '-';
                        permissions[8] = (buf.st_mode & S_IXOTH) ? 'x' : '-';
                        permissions[9] = '\0';

                        if (strcmp(permissions, permission) != 0)
                            validP = 0;
                    }
                }
                if (validP == 1 && validS == 1)
                    printf("%s\n", finalPath);
            }
        }
        closedir(dir);
    }
}

void recursiv(char path[500], int size, char permission[50]) ///////////////2
{
    DIR *dir = opendir(path);
    struct dirent *entry;
    struct stat buf;
    if (dir == 0)
        printf("ERROR\n invalid directory path\n");
    else
    {
        while ((entry = readdir(dir)) != 0)
        {
            if (strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0)
            {
                char finalPath[500];
                snprintf(finalPath, 500, "%s/%s", path, entry->d_name);
                lstat(finalPath, &buf);
                int validS = 1, validP = 1;
                if (size != -1)
                {
                    if (S_ISREG(buf.st_mode))
                    {
                        if (size < buf.st_size)
                            validS = 0;
                    }
                    if (S_ISDIR(buf.st_mode))
                        validS = 0;
                }

                if (strcmp(permission, ""))
                {
                    char permissions[40];

                    permissions[0] = (buf.st_mode & S_IRUSR) ? 'r' : '-';
                    permissions[1] = (buf.st_mode & S_IWUSR) ? 'w' : '-';
                    permissions[2] = (buf.st_mode & S_IXUSR) ? 'x' : '-';
                    permissions[3] = (buf.st_mode & S_IRGRP) ? 'r' : '-';
                    permissions[4] = (buf.st_mode & S_IWGRP) ? 'w' : '-';
                    permissions[5] = (buf.st_mode & S_IXGRP) ? 'x' : '-';
                    permissions[6] = (buf.st_mode & S_IROTH) ? 'r' : '-';
                    permissions[7] = (buf.st_mode & S_IWOTH) ? 'w' : '-';
                    permissions[8] = (buf.st_mode & S_IXOTH) ? 'x' : '-';
                    permissions[9] = '\0';

                    if (strcmp(permissions, permission) != 0)
                        validP = 0;
                }
                if (validP == 1 && validS == 1)
                    printf("%s\n", finalPath);
                if (S_ISDIR(buf.st_mode))
                    recursiv(finalPath, size, permission);
            }
        }
        closedir(dir);
    }
}

void parse(char path[500]) /////////////////3
{
    int sf = 0, size = 0, magic = 0, version = 0, n = 0, tip = 0, offset = 0, ssize = 0;
    int a[7] = {40, 47, 23, 62, 97, 53, 12};
    char nume[50] = {0};
    sf = open(path, O_RDONLY);
    read(sf, &magic, 1);
    read(sf, &size, 2);
    if (magic != 'W')
        printf("ERROR\nwrong magic");
    else
    {
        read(sf, &version, 4);
        if (!(version >= 32 && version <= 118))
            printf("ERROR\nwrong version");
        else
        {
            read(sf, &n, 1);
            if (!(n >= 6 && n <= 11))
                printf("ERROR\nwrong sect_nr");
            else
            {
                int auxiliar = 0;
                for (int i = 0; i < n; i++)
                {
                    read(sf, &nume, 16);
                    read(sf, &tip, 1);
                    read(sf, &offset, 4);
                    read(sf, &ssize, 4);
                    for (int j = 0; j < 7; j++)
                        if (tip == a[j])
                            auxiliar++;
                }
                if (auxiliar != n)
                    printf("ERROR\nwrong sect_types");
                else
                {
                    lseek(sf, 8, SEEK_SET);
                    printf("SUCCESS\n");
                    printf("version=%d\n", version);
                    printf("nr_sections=%d\n", n);
                    for (int j = 1; j <= n; j++)
                    {
                        read(sf, &nume, 16);
                        read(sf, &tip, 1);
                        read(sf, &offset, 4);
                        read(sf, &ssize, 4);
                        printf("section%d: %s %d %d\n", j, nume, tip, ssize);
                    }
                }
            }
        }
    }
}


int main(int argc, char **argv)
{
    /////////////////1////////////////
    int l = 0, r = 0, size = -1, section, line;
    char path[500] = "", aux[50] = "", permission[50] = "";
    if (argc > 1 && strcmp(argv[1], "variant") == 0)
        printf("55278\n");
    ///////////////2////////////////////
    if (argc > 1)
    {
        for (int i = 1; i < argc; i++)
            if (strcmp(argv[i], "list") == 0)
            {
                l = 1;
            }
            else if (strcmp(argv[i], "recursive") == 0)
            {
                r = 1;
            }
            else if (strstr(argv[i], "path=") == argv[i])
            {
                char *path_value = strchr(argv[i], '=') + 1;
                strncpy(path, path_value, sizeof(path));
            }
            else if (strstr(argv[i], "size_smaller=") == argv[i])
            {
                char *size_value = strchr(argv[i], '=') + 1;
                strncpy(aux, size_value, sizeof(aux));
                size = atoi(aux);
            }
            else if (strstr(argv[i], "permissions=") == argv[i])
            {
                char *permission_value = strchr(argv[i], '=') + 1;
                strncpy(permission, permission_value, sizeof(permission));
            }
        if (l == 1)
        {
            printf("SUCCESS\n");
            if (r == 0)
                list(path, size, permission);
            else
                recursiv(path, size, permission);
        }
    }
    ///////////////3/////////////////////
    if (argc > 1)
    {
        l = 0;
        for (int i = 1; i < argc; i++)
            if (strcmp(argv[i], "parse") == 0)
            {
                l = 1;
            }
            else if (strstr(argv[i], "path=") == argv[i])
            {
                char *path_value = strchr(argv[i], '=') + 1;
                strncpy(path, path_value, sizeof(path));
            }

        if (l == 1)
            parse(path);
    }

    return 0;
}